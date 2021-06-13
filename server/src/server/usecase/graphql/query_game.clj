(ns server.usecase.graphql.query-game
  (:require
   [taoensso.timbre :as timbre]
   [server.interface.gateway.database.board-game :as repository.board-game]
   [server-utils.error :refer [border-error]]
   [server.domain.error :as domain.error]
   [com.walmartlabs.lacinia.executor :as le]))

(defn ->http [[success failure]]
  (if failure failure success))

(defn game
  [db ctx args value]
  (let [{:keys [id]} args
        opt (cond-> {}
              (le/selects-field? ctx :BoardGame/designers) (assoc :with-designer? true))]
    (timbre/info "query: game " args value)
    (when (le/selects-field? ctx :BoardGame/designers)
      (timbre/info "warn: n+1 is raised "))
    (repository.board-game/get-board-game-by-id db id opt)))
