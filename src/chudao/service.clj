(ns chudao.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.interceptor.helpers :as interceptor]
            [clojure.data.json :as json]
            [ring.util.response :as ring-resp]
            [ring.middleware.multipart-params :as multipart-params]
            [chudao.service.auth :as auth]
            [chudao.service.binary :as binary]
            [chudao.service.query :as query]
            [chudao.service.product :as product]
            [chudao.html.forms :as forms]
            [korma.db :as korma-db]
            [io.pedestal.http :as bootstrap]))

(defn home-page
  [request]
  (ring-resp/response "Wo men Chudao la!!"))

(def routes
  `[[["/" {:get home-page}
      ^:interceptors [(body-params/body-params) bootstrap/html-body]
      ["/auth/login" {:post auth/login}]
      ["/auth/register" {:post auth/register}]
      ["/binary/upload" {:get forms/upload-file
                         :post binary/upload-file}]
      ["/binary/download" {:post binary/download-file
                           :get binary/download-file-get}]
      ["/query/user/:user-id" {:get query/by-user-id}]
      ["/query/product/tags" {:post query/by-tags}]
      ["/query/product/ids" {:post query/by-ids}]
      ["/product/add" {:post product/add}]
      ["/product/add/form" {:post product/add-form}]
      ]]])

(korma-db/defdb db (korma-db/mysql {:db (System/getenv "DB_NAME")
                                    :user (System/getenv "DB_USER")
                                    :password (System/getenv "DB_PASSWORD")
                                    :host (System/getenv "DB_HOST")
                                    :port (System/getenv "DB_PORT")}))

;; Consumed by chudao.server/create-server
;; See http/default-interceptors for additional options you can configure
(def service {:env :prod
              ;; You can bring your own non-default interceptors. Make
              ;; sure you include routing and set it up right for
              ;; dev-mode. If you do, many other keys for configuring
              ;; default interceptors will be ignored.
              ;; ::http/interceptors []
              ::http/routes routes

              ;; Uncomment next line to enable CORS support, add
              ;; string(s) specifying scheme, host and port for
              ;; allowed source(s):
              ;;
              ;; "http://localhost:8080"
              ;;
              ;;::http/allowed-origins ["scheme://host:port"]

              ;; Root for resource interceptor that is available by default.
              ::http/resource-path "/public"

              ;; Either :jetty, :immutant or :tomcat (see comments in project.clj)
              ::http/type :jetty
              ;;::http/host "localhost"
              ::http/port (Integer. (or (System/getenv "PORT") 7002))
              ;; Options to pass to the container (Jetty)
              ::http/container-options {:h2c? true
                                        :h2? false
                                        ;:keystore "test/hp/keystore.jks"
                                        ;:key-password "password"
                                        ;:ssl-port 8443
                                        :ssl? false}})

