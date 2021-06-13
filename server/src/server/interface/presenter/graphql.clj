(ns server.interface.presenter.graphql
  (:require
   [clojure.data.json :as json]
   [clojure.spec.alpha :as s]
   [server-utils.domain.http :as util-domain.http]
   [server.domain.error :as domain.error]
   [server.domain.graphql :as domain.graphql]))

(s/def ::http-output-data (s/keys :req-un [::util-domain.http/status]))
(s/fdef ->http
  :args (s/cat :args
               (s/or :success (s/tuple ::domain.graphql/result nil?)
                     :failure (s/tuple nil? ::domain.error/error)))
  :ret (s/or :success ::http-output-data
             :failure ::domain.error/error))

(defn ->http
  [[output-model error]]
  (if (nil? error)
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/write-str output-model)}
    error))
