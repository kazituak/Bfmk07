# 初期画面

工場システムにおける部署情報を管理するページです。
主な機能として部署情報のDBへの登録、リストからの削除、DBを参照した
部署リストの閲覧ができます。

![部署情報_初期画面](https://github.com/kazituak/Bfmk07/assets/140673635/ee2319b0-8521-4527-af56-d2fb4b90e701)

# URL

# 使用技術
・Spring Boot

・MySQL

・

・

# 機能一覧
・部署情報登録機能

・ログイン機能(この状態では確認できないので良いか不明

・複数削除機能(リストからの非表示設定

・部署リスト機能

・ページネーション機能(8項目ずつ

・メニュー画面へ戻る機能

・初期画面に戻る機能

・規定資格による機能の活性化機能

# 工夫した点

・登録情報として入力した部署IDと一致する非表示状態の部署IDがあった場合、登録処理の代わりに更新処理を行う

・部署名略称が空白の場合も、条件付きで自動的に設定した状態で処理を行う

・チェックボタンが押されていない状態で削除ボタンを押した場合に選択していない旨の警告文を出力

# その他

自分の言葉で説明を加える、ある程度相手からの質問を誘導することを前提として制作した説明文が良い？

インターン先で作成



・どのように分けるか[候補、入力フォームとボタンとリスト]

主な機能

登録、削除、リスト




：入力フォーム

：ボタン

：リスト

機能

・登録

・複数削除（非表示切り替え）

・メニュー画面への移動

・初期状態への更新

・登録時削除済み部署IDに重複するものがあった場合、部署IDを使い回す

・部署IDフォーム

・部署名フォーム

・部署名略称フォーム

・適応情報フォーム

・部署リスト

・ページネーション

・次ページボタン

・前ページボタン

・１ページ目ボタン

・最終ページボタン

・合計件数表示

・最大ページ数表示

・現在ページ表示

・エラーメッセージ[部署ID系]

・エラーメッセージ[部署名系]

・エラーメッセージ[部署名略称系]

・エラーメッセージ[適用日系]

・エラーメッセージ[適用日時間矛盾系]

・エラーメッセージ[複合系]

・完了メッセージ[登録]

・完了メッセージ[削除]
