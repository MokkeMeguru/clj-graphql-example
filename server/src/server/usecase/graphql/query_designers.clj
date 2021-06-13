(ns server.usecase.graphql.query-designers
  (:require [taoensso.timbre :as timbre]
            [server.interface.gateway.database.designer :as repository.designer]))

(defn designers
  [db ctx args value]
  (timbre/info "query: designers" args value)
  (repository.designer/get-designers db))
