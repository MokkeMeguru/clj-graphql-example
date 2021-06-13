(ns server.infrastructure.database.local.core
  (:require [integrant.core :as ig]
            [clojure.java.io :as io]
            [clojure.edn :as edn]))

(defrecord Boundary [spec])

(defmethod ig/init-key ::database
  [_ {:keys [local-data]}]
  (let [data (-> local-data
                 io/resource
                 slurp
                 edn/read-string)]
    (->Boundary {:datasource data})))
