(ns dev
  (:require
   [server.core :as core]
   [integrant.repl :as igr]))

(defn start
  ([]
   (start core/config-file))
  ([config-file]
   (igr/set-prep! (constantly (core/load-config config-file)))
   (igr/prep)
   (igr/init)))

(defn stop []
  (igr/halt))

(defn restart []
  (igr/reset-all))
