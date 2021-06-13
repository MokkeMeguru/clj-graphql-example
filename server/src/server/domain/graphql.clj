(ns server.domain.graphql
  (:require [clojure.spec.alpha :as s]))

(s/def ::query string?)
(s/def ::variables map?)
(s/def ::order (s/keys :req-un [::query ::variables]))
(s/def ::result map?)
