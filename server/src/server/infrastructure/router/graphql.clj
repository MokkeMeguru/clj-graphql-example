(ns server.infrastructure.router.graphql
  (:require
   [server-utils.infrastructure.router :refer [handler->]]
   [server.interface.controller.graphql :as graphql-controller]
   [server.interface.presenter.graphql :as graphql-presenter]
   [server.usecase.graphql :as graphql-usecase]))

(defn graphql-handler [schema input-data]
  (handler->
   input-data
   graphql-controller/http->
   (partial graphql-usecase/graphql schema)
   graphql-presenter/->http))

(defn router [schema]
  ["/graphql" {:post {:parameters {:body map?}
                      :responses {200 {:body string?}}
                      :handler (partial graphql-handler schema)}}])
