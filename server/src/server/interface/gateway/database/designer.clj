(ns server.interface.gateway.database.designer
  (:require
   [clojure.spec.alpha :as s]
   [server.domain.designer :as domain.designer]
   [server.interface.gateway.database.mock :refer [with-mock]]))

(defprotocol Designer
  (get-designers [db])
  (get-designer-by-id [db id])
  (get-designer-by-ids [db ids]))

(defn designer-repository? [inst]
  (satisfies? Designer inst))

(s/def ::designer-repository designer-repository?)

(s/fdef get-designers
  :args (s/cat :db ::designer-repository)
  :ret ::domain.designer/designers)

(s/fdef get-designer-by-id
  :args (s/cat :db ::designer-repository
               :id ::domain.designer/id)
  :ret (s/or :exist ::domain.designer/designer
             :not-exist nil?))

(s/fdef get-designer-by-ids
  :args (s/cat :db ::designer-repository
               :ids (s/coll-of ::domain.designer/id))
  :ret ::domain.designer/designers)

;; (local-mock/with-mock
;;   (fn [db]
;;     (get-designers db)
;;     (get-designers-by-id db "200")))
