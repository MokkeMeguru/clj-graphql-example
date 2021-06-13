(ns server.interface.gateway.database.board-game
  (:require
   [clojure.spec.alpha :as s]
   [server.domain.board-game :as domain.board-game]
   [clojure.java.io :as io]
   [clojure.edn :as edn]))

(defprotocol BoardGame
  (get-board-games [db opt])
  (get-board-game-by-id [db id opt]))

(defn board-game-repository? [inst]
  (satisfies? BoardGame inst))

(s/def ::board-game-repository board-game-repository?)

(s/def ::with-designer? boolean?)

(s/fdef get-board-games
  :args (s/cat :db ::board-game-repository
               :opts (s/keys :opt-un [::with-designer?]))
  :ret (s/coll-of ::domain.board-game/board-game))

(s/fdef get-board-game-by-id
  :args (s/cat :db ::board-game-repository
               :id ::domain.board-game/id
               :opts (s/keys :opt-un [::with-designer?]))
  :ret (s/or :exist ::domain.board-game/board-game
             :not-exist nil?))

;; (def board-game-repository
;;   (server.infrastructure.database.local.core/->Boundary
;;    {:datasource
;;     (-> "cgg-data.edn" io/resource slurp edn/read-string)}))

;; (get-board-games board-game-repository)
;; (get-board-game-by-id board-game-repository "1234")
