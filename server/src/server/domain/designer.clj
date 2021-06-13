(ns server.domain.designer
  (:require [clojure.spec.alpha :as s]))

(s/def ::id string?)
(s/def ::name string?)

(s/def ::designer (s/keys :req-un [::id ::name]))
(s/def ::designers (s/coll-of ::designer))
