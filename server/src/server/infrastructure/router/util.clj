(ns server.infrastructure.router.util
  (:require [taoensso.timbre :as timbre]))

(defn my-wrap-cors
  "Wrap the server response in a Control-Allow-Origin Header to
  allow connections from the web app."
  [handler]
  (fn [request]
    (timbre/warn "Access Origin: " (-> request :headers (get "origin")))
    (let [response (handler request)]
      (-> response
          (assoc-in [:headers "Access-Control-Allow-Credentials"] "true")
          (assoc-in [:headers "Access-Control-Allow-Origin"] "localhost:3031")
          (assoc-in [:headers "Access-Control-Allow-Headers"] "authorization,content-type")
          (assoc-in [:headers "Access-Control-Allow-Methods"] "POST,GET,OPTIONS,DELETE,PUT,UPDATE,PATCH")))))
