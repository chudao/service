(defproject chudao "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [io.pedestal/pedestal.service "0.5.0"]
                 ;; Remove this line and uncomment one of the next lines to
                 ;; use Immutant or Tomcat instead of Jetty:
                 ;;[io.pedestal/pedestal.jetty "0.5.0"]
                 ;; [io.pedestal/pedestal.immutant "0.4.2-SNAPSHOT"]
                 [io.pedestal/pedestal.tomcat "0.5.0"]
                 [amazonica "0.3.61"]
                 [mysql/mysql-connector-java "5.1.6"]
                 [korma "0.4.2"]
                 [com.fasterxml.jackson.core/jackson-databind "2.6.0"]
                 [ch.qos.logback/logback-classic "1.1.7" :exclusions [org.slf4j/slf4j-api]]
                 [enlive "1.1.6"]
                 [crypto-password "0.2.0"]
                 [com.novemberain/monger "3.0.2"]
                 [org.clojure/data.json "0.2.6"]
                 [org.slf4j/jul-to-slf4j "1.7.21"]
                 [org.slf4j/jcl-over-slf4j "1.7.21"]
                 [org.slf4j/log4j-over-slf4j "1.7.21"]]
  :min-lein-version "2.0.0"
  :resource-paths ["config", "resources"]
  :plugins [[info.sunng/lein-bootclasspath-deps "0.2.0"]]
  :boot-dependencies [;; See: https://www.eclipse.org/jetty/documentation/current/alpn-chapter.html#alpn-versions
                      [org.mortbay.jetty.alpn/alpn-boot "8.1.4.v20150727" :prepend true] ;; JDK 1.8.0_51
                      ;[org.mortbay.jetty.alpn/alpn-boot "8.1.3.v20150130"] ;; JDK 1.8.0_31/40/45
                      ;[org.mortbay.jetty.alpn/alpn-boot "8.1.2.v20141202"] ;; JDK 1.8.0_25
                      ;[org.mortbay.jetty.alpn/alpn-boot "8.1.0.v20141016" :prepend true] ;; JDK 1.8.0_20 (1.8 up to _20)
                      ]
  :profiles {:dev {:jvm-opts ["-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5010"]
                   :aliases {"run-dev" ["trampoline" "run" "-m" "chudao.server/run-dev"]
                             "debug" ["with-profile" "dev" "run"]}
                   :dependencies [[io.pedestal/pedestal.service-tools "0.5.0"]]}
             :uberjar {:aot [chudao.server]}}
  :main ^{:skip-aot true} chudao.server)

