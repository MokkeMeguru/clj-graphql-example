(ns server.interface.gateway.database.mock
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [server.infrastructure.database.local.proto :as proto]))

(defn with-mock
  "with mock with local db"
  ([funcs]
   (with-mock funcs
     (proto/->Boundary
      {:datasource (-> "cgg-data.edn" io/resource slurp edn/read-string)})))
  ([funcs local-boundary]
   (funcs local-boundary)))
