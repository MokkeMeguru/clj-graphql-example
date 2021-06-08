(defproject server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 ;; アプリの状態管理
                 [integrant "0.8.0"]
                 [integrant/repl "0.3.2"]

                 ;; graphql のためのライブラリ
                 [com.walmartlabs/lacinia "0.39-alpha-5"]
                 [org.flatland/ordered "1.5.9"]

                 ;; graphql server のためのライブラリ (?)
                 [com.walmartlabs/lacinia-pedestal "0.15.0"]

                 ;; 環境変数の読み込みのためのライブラリ
                 [environ "1.2.0"]

                 ;; ロギング処理のためのライブラリ
                 [com.taoensso/timbre "5.1.2"]
                 [com.fzakaria/slf4j-timbre "0.3.20"]

                 ;; テスト、 Spec のためのライブラリ
                 [orchestra "2021.01.01-1"]
                 [org.clojure/test.check "1.1.0"]

                 ;; CLI コマンドの実行のためのライブラリ
                 [org.clojure/tools.cli "1.0.206"]

                 ;; JSON 処理、時刻処理、文字列処理のためのライブラリ
                 [clj-time "0.15.2"]
                 [cheshire "5.10.0"]
                 [camel-snake-kebab "0.4.2"]]

  :main ^:skip-aot server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:resource-paths ["dev-resources"]}})
