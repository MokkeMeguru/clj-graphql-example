(ns server.usecase.graphql.query-designer
  (:require [server.interface.gateway.database.designer :as repository.designer]
            [taoensso.timbre :as timbre]))

(defn designer
  [db ctx args value]
  (let [{:keys [id]} args]
    (timbre/info "query: designer " ctx args value)
    (repository.designer/get-designer-by-id db id)))
