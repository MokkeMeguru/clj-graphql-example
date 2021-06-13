(ns server.domain.board-game
  (:require [clojure.spec.alpha :as s]
            [server.domain.designer :as domain.designer]))

(s/def ::id string?)
(s/def ::name string?)

(s/def ::board-game (s/keys :req-un [::id ::name]
                            :opt-un [::domain.designer/designers]))
