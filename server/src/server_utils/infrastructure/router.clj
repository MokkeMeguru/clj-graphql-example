(ns server-utils.infrastructure.router
  (:require
   [server-utils.error :refer [err->>]]))

(defmacro handler->
  "basic handler for the triple: controller, usecase, presenter"
  [input-data controller usecase presenter]
  `(~presenter
    (err->> ~input-data
            ~controller
            ~usecase)))
