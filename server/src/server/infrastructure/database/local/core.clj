(ns server.infrastructure.database.local.core
  (:require [integrant.core :as ig]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.spec.alpha :as s]
            [server.infrastructure.database.local.proto :as proto]

            [server.interface.gateway.database.board-game :refer [board-game-repository?]]
            [server.interface.gateway.database.designer :refer [designer-repository?]]

            [server.interface.gateway.database.local.board-game]
            [server.interface.gateway.database.local.designer]))

(defmethod ig/init-key ::database
  [_ {:keys [local-data]}]
  (let [data (-> local-data
                 io/resource
                 slurp
                 edn/read-string)
        spec (s/and
              board-game-repository?
              designer-repository?)

        boundary (proto/->Boundary {:datasource data})]
    (if (s/valid? spec boundary)
      boundary
      (throw (ex-info "invalid boundary : not safisfied the boundary spec" (s/conform spec boundary))))))
