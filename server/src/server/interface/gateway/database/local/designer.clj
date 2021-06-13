(ns server.interface.gateway.database.local.designer
  (:require
   [server.interface.gateway.database.designer :refer [Designer]]
   [server-utils.sequence :refer [find-first]]))

(extend-protocol Designer
  server.infrastructure.database.local.core.Boundary
  (get-designers [{{:keys [datasource]} :spec}]
    (-> datasource :designers))
  (get-designer-by-id [{{:keys [datasource]} :spec} id]
    (find-first (fn [designer] (= (:id designer) id)) (-> datasource :designers)))
  (get-designer-by-ids [{{:keys [datasource]} :spec} ids]
    (filter (fn [designer] (some #(= (:id designer) %) ids)) (:designers datasource))))
