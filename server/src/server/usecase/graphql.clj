(ns server.usecase.graphql
  (:require [clojure.spec.alpha :as s]
            [com.walmartlabs.lacinia :as lacinia]
            [server.domain.graphql :as domain.graphql]
            [server-utils.error :refer [err->>]]
            [integrant.core :as ig]
            [integrant.repl :as igr]
            [com.walmartlabs.lacinia.parser.schema :as parser.schema]
            [com.walmartlabs.lacinia.schema :as schema]
            [server.domain.error :as domain.error]
            [com.walmartlabs.lacinia.tracing :as tracing]))

(s/def ::input-model ::domain.graphql/order)
(s/def ::output-model ::domain.graphql/result)

;; lacinia {:timeout-ms 1000 :timeout-error domain.error/graphql-query-timeout} not works
(defn execute [{:keys [schema input-model] :as m}]
  (let [{:keys [query variables]} input-model
        result (lacinia/execute schema query variables nil)]
    [result nil]))

(defn graphql
  [schema input-model]
  (err->>
   {:schema schema
    :input-model input-model}
   execute))

;; (graphql
;;  (-> {:schema "cgg-schema.graphql"}
;;      server.infrastructure.graphql.core/read-schema
;;      (parser.schema/parse-schema {:resolvers (server.infrastructure.graphql.core/resolver nil)})
;;      schema/compile)
;;  {:query "
;; query{
;;   game_by_id(id:\"1234\") {
;;     description
;;     name
;;     play_time
;;     summary
;;   }
;; }
;; "
;;   :variable {}})
;; (graphql
;;  (-> {:schema "cgg-schema.graphql"}
;;      server.infrastructure.graphql.core/read-schema
;;      (parser.schema/parse-schema {:resolvers (server.infrastructure.graphql.core/resolver nil)})
;;      schema/compile)
;;  {:query "
;; query{
;; 	node(id:\"1234\") {
;;     id
;;   	... on BoardGame {
;;        name
;;   	}
;; 	}
;; }
;; "
;;   :Variable {}})
