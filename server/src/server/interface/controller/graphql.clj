(ns server.interface.controller.graphql
  (:require
   [clojure.spec.alpha :as s]
   [server.domain.graphql :as domain.graphql]
   [server.domain.error :as domain.error]))

(s/fdef http->
  :args (s/cat :args map?)
  :ret (s/or :success (s/tuple ::domain.graphql/order nil?)
             :failure (s/tuple nil? map?)))

(defn http->
  [input-data]
  (try
    (let [input-model (-> input-data :parameters :body)
          conformed-input-model (s/conform ::domain.graphql/order input-model)]
      (when (= ::s/invalid conformed-input-model)
        (throw (ex-info "" (domain.error/input-data-is-invalid (s/explain-str ::domain.graphql/order input-model)))))
      [conformed-input-model nil])
    (catch Exception e
      [nil (ex-data e)])))

;; (st/instrument)
;; (http-> {:body "{ \"query\": \"Hello\"}"})
;; (http-> {:body "{ query: \"Hello\"}"})
;; (http-> {:body "1"})
