(ns server.usecase.graphql.query-games
  (:require [taoensso.timbre :as timbre]
            [com.walmartlabs.lacinia.executor :as le]
            [server.interface.gateway.database.board-game :as repository.board-game]))

(defn games
  [db ctx args value]
  (timbre/info "query: games" args value)
  (let [opts (cond-> {}
               (le/selects-field? ctx :BoardGame/designers) (assoc :with-designer? true))]
    (when (le/selects-field? ctx :BoardGame/designers)
      (timbre/info "warn: n+1 is raised "))
    (repository.board-game/get-board-games db opts)))
