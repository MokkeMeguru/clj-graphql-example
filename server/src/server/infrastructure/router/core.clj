(ns server.infrastructure.router.core
  (:require
   [reitit.ring :as ring]
   [reitit.coercion.spec]
   [server.infrastructure.router.graphql :as graphql-router]
   [server-utils.infrastructure.router.util :refer [form-wrap-cors]]

   [ring.logger :refer [wrap-with-logger]]

   [reitit.dev.pretty :as pretty]
   [reitit.ring.coercion :as coercion]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.exception :as exception]
   [reitit.ring.middleware.multipart :as reitit-multipart]
   [reitit.ring.middleware.parameters :as parameters]

   [muuntaja.core :as m]
   [integrant.core :as ig]
   [taoensso.timbre :as timbre]
   [clojure.java.io :as io]))

(defn app [schema]
  (ring/ring-handler
   (ring/router
    [""
     (graphql-router/router schema)
     ["/" (constantly {:status 200
                       :headers {"Content-Type" "text/html"}
                       :body (slurp (io/file "resources/static/playground.html"))})]]
    {:exception pretty/exception
     :data {:coercion reitit.coercion.spec/coercion
            :muuntaja m/instance
            :middleware
            [;; query-params & form-params
             parameters/parameters-middleware
               ;; content-negotiation
             muuntaja/format-negotiate-middleware
               ;; encoding response body
             muuntaja/format-response-middleware
               ;; exception handling
             exception/exception-middleware
               ;; decoding request body
             muuntaja/format-request-middleware
               ;; coercing response bodys
             coercion/coerce-response-middleware
               ;; coercing request parameters
             coercion/coerce-request-middleware
               ;; multipart
             reitit-multipart/multipart-middleware]}})

   (ring/routes
    (ring/create-default-handler))
   {:middleware [wrap-with-logger
                 (form-wrap-cors {})]}))

(defmethod ig/init-key ::router [_ {:keys [env schema]}]
  (timbre/info "router got: env" env)
  (timbre/info "router got: schema" schema)
  (app schema))
