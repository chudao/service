(ns chudao.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [chudao.service.auth :as auth]
            [chudao.service.binary :as binary]
            [chudao.service.query :as query]
            [chudao.service.product :as product]
            [chudao.service.request :as request]
            [chudao.interceptor.security :as security]
            [chudao.html.forms :as forms]
            [korma.db :as korma-db]
            [io.pedestal.http :as bootstrap]))

(defn home-page
  [request]
  (ring-resp/response "Wo men Chudao la!!"))

(def routes
  `[[["/" {:get home-page}
      ^:interceptors [(body-params/body-params) bootstrap/html-body security/check-auth-status]

      ["/auth/login" {:post auth/login}]
      ["/auth/register" {:post auth/register}]

      ["/binary/upload" {:get forms/upload-file
                         :post binary/upload-file}]
      ["/binary/download" {:post binary/download-file
                           :get binary/download-file-get}]
      ["/query/product/tags" {:post query/find-product-by-tags}]
      ["/query/product/ids" {:post query/find-products-by-ids}]
      ["/query/file/product-ids" {:post query/find-files-by-product-ids}]
      ["/query/file/user-id" {:post query/find-files-by-user-ids}]
      ["/query/request/user-id" {:get query/find-request-by-user-id}]

      ["/product/add" {:post product/add}]
      ["/product/add/form" {:post product/add-form}]

      ["/request/add" {:post request/add}]
      ["/request/handle" {:post request/handle}]
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
              ;::http/resource-path "/public"

              ;; Either :jetty, :immutant or :tomcat (see comments in project.clj)
              ::http/type :tomcat
              ;;::http/host "localhost"
              ::http/port (Integer. (or (System/getenv "PORT") 7002))
              ;::http/enable-session {}
              })

