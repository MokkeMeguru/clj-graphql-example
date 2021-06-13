(ns server.interface.gateway.database.local.board-game
  (:require
   [server.interface.gateway.database.board-game :refer [BoardGame] :as irepository]
   [server-utils.sequence :refer [find-first]]))

(extend-protocol BoardGame
  server.infrastructure.database.local.core.Boundary
  (get-board-games [{{:keys [datasource]} :spec} {:keys [with-designer?]}]
    (if with-designer?
      (map (fn [game]
             (assoc game :designers
                    (filter (fn [designer] (some (partial = (:id designer)) (:designers game))) (:designers datasource))))
           (:games datasource))
      (map (fn [game] (dissoc game :designers) (:games datasource)))))
  (get-board-game-by-id [{{:keys [datasource]} :spec :as db} id opt]
    (find-first (fn [game] (= (:id game) id)) (irepository/get-board-games db opt))))
