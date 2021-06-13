(ns server-utils.domain.http
  (:require [clojure.spec.alpha :as s]))

(s/def ::status pos-int?)
