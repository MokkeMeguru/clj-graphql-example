(ns server.infrastructure.server
  (:require [integrant.core :as ig]
            [taoensso.timbre :as timbre]
            [ring.adapter.jetty :as jetty]))

(defmethod ig/init-key ::server [_ {:keys [env router port]}]
  (timbre/info "server is running in port" (or (:port env) port))
  (timbre/info "router is " router)
  (jetty/run-jetty router {:port 4030 :join? false}))

(defmethod ig/halt-key! ::server [_ server]
  (timbre/info "stop server")
  (.stop server))
