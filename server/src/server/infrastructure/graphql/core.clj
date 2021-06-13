(ns server.infrastructure.graphql.core
  (:require
   [integrant.core :as ig]
   [clojure.java.io :as io]
   [com.walmartlabs.lacinia.util :as util]
   [com.walmartlabs.lacinia.parser.schema :as parser.schema]
   [com.walmartlabs.lacinia.schema :as schema]

   [server.usecase.graphql.query-designer :as usecase.query-designer]
   [server.usecase.graphql.query-game :as usecase.query-game]
   [server.usecase.graphql.query-games :as usecase.query-games]
   [server.usecase.graphql.query-designers :as usecase.query-designers]
   [taoensso.timbre :as timbre]))

(defn read-schema [{:keys [schema]}]
  (-> schema
      io/resource
      slurp))

(defn resolver [database]
  {:Query {:game (partial usecase.query-game/game database)
           :designer (partial usecase.query-designer/designer database)
           :games (partial usecase.query-games/games database)
           :designers (partial usecase.query-designers/designers database)}})

(defmethod ig/init-key ::schema
  [_ {:keys [env database graphql-schema]}]
  (->
   {:schema (or (:graphql-schema env) graphql-schema)}
   read-schema
   (parser.schema/parse-schema {:resolvers (resolver database)})
   schema/compile))
