(ns server.core
  (:gen-class)
  (:require
   [server.infrastructure.schema :as s]
   [com.walmartlabs.lacinia :as lacinia]
   [clojure.walk :as walk]
   [ring.adapter.jetty :as jetty]
   [ring.util.request :as request]
   [reitit.ring :as ring]
   [clojure.data.json :as json]
   [ring.middleware.resource :as resource]
   [ring.middleware.content-type :as content-type]
   [ring.middleware.not-modified :as not-modified]
   [reitit.core :as r]

   [server.infrastructure.router.util :refer [my-wrap-cors]])

  (:import (clojure.lang IPersistentMap)))

(def schema (s/load-schema))

(defn simplify
  "coverts all ordered maps nested within the map into standard hash maps,
  and sequences into vectors, which makes for easier constants in the tests,
  and eliminates ordering problems."
  [m]
  (walk/postwalk
   (fn [node]
     (cond
       (instance? IPersistentMap node) (into {} node)
       (seq? node) (vec node)
       :else node))
   m))

(defn q
  [query-string]
  (-> (lacinia/execute schema query-string nil nil)
      simplify))

(defn graphql-handler [request]
  (let [graphql-request (json/read-str (request/body-string request) :key-fn keyword)
        {:keys [query variables]} graphql-request
        result (lacinia/execute schema query variables nil)]
    {:status 200
     :body (json/write-str result)
     :headers {"Content-Type" "application/json"}}))

(def app
  (-> (ring/ring-handler
       (ring/router
        ["/graphql" {:post graphql-handler}])
       (ring/routes
        (ring/create-default-handler
         {:not-found (ring/create-file-handler {:root "static"
                                                :index-files ["playground.html"]})}))
       {:middleware [my-wrap-cors]})
      (resource/wrap-resource "static")
      content-type/wrap-content-type
      not-modified/wrap-not-modified))

(defn start-server []
  (jetty/run-jetty app {:join? false :port 3030}))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (start-server))

;; (q "{ game_by_id(id: \"1234\") { id name summary }}")
