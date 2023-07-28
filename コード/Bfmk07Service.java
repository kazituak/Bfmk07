package com.seizou.kojo.domain.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seizou.kojo.domain.dto.Bfmk07Dto;
import com.seizou.kojo.domain.entity.Bfmk07BelongingEntity;
import com.seizou.kojo.domain.form.Bfmk07EntryForm;
import com.seizou.kojo.domain.repository.Bfmk07Repository;

/**
 * 部署情報のServiceクラス
 * @author b-forme_Kajiura
 *
 */
@Service
@Transactional
public class Bfmk07Service {

	/** リポジトリクラス */
	@Autowired
	Bfmk07Repository repository;

	/**
	 * リストの取得を行うメソッド
	 * @param 部署情報DTO
	 * @return 表示リスト
	 */
	public List<Bfmk07BelongingEntity> pagination(
			Bfmk07Dto bfmk07Dto) {
		// 現行ページに該当するリストの開始行を取得する
		Integer listnumber = (bfmk07Dto.getPage() * 8);
		// 現行ページに該当するリストの出力を実行する
		List<Bfmk07BelongingEntity> list =
				repository.pageSelect(listnumber);
		return list;
	}

	/**
	 * 登録処理を管理するメソッド
	 * @param 入力フォーム
	 * @param 部署情報DTO
	 * @return エラー判定
	 */
	public boolean bfmk07Insert(
			Bfmk07EntryForm entryform,
			Bfmk07Dto bfmk07Dto) {
		// 重複があるか確認
		if (repository.entry_subCheck( entryform.getAffilicate_id())) {
			// 更新処理
			if (repository.entry_sub(entryform, bfmk07Dto)) {
				// 正常に処理が完了した場合
				bfmk07Dto.setMessage("mseven017");
				return true;
			}
		// 登録処理
		} else if (repository.entry_exe(entryform, bfmk07Dto)) {
			// 正常に処理が完了した場合
			bfmk07Dto.setMessage("mseven017");
			return true;
		}
		// エラーが発生した場合
		bfmk07Dto.setMessage("mseven015");
		return false;
	}

	/**
	 * 記入情報を確認するメソッド
	 * @param 入力フォーム
	 * @return エラー識別用配列
	 */
	public boolean[] checkEntryError(
			Bfmk07EntryForm entryform) {
		// エラー判定を記録する配列
		boolean[] errorJudge = new boolean[8];
		// 詳細判定を記録する配列
		boolean[] additionJudge = new boolean[3];
		// 適用開始日の設定予定変数
		Date strtDate = null;
		// 適用終了日の設定予定変数
		Date finDate = null;
		
		// 部署名略称に記入があるか確認
		if (entryform.getAffilicate_name_r().equals("")) {
			// ない場合補足判定にtrue
			additionJudge[0] = true;
		}
		// 適用終了日に記入があるか確認
		if (entryform.getApply_fin_date().equals("")) {
			// ない場合補足判定にtrue
			additionJudge[1] = true;
			// 後のリポジトリ処理の為 null に変更
			entryform.setApply_fin_date(null);
		}
		// 部署IDに記入があるか確認
		if (entryform.getAffilicate_id().equals("")) {
			// 記入がない場合エラー
			errorJudge[0] = true;
			return errorJudge;
		} else {
			// ある場合部署IDの重複確認
			if (repository.entryCheck(entryform.getAffilicate_id())) {
				// ある場合エラー
				errorJudge[1] = true;
				return errorJudge;
			}
		}
		// 部署名に記入があるか確認
		if (entryform.getAffilicate_name().equals("")) {
			// 記入がない場合エラー
			errorJudge[2] = true;
			return errorJudge;
			// ある場合部署名が 20 文字以内か確認
		} else if (entryform.getAffilicate_name().length() > 20) {
			// 20文字以上である場合補足判定にtrue
			additionJudge[2] = true;
			// 部署名略称に記入がない場合
		} else if (additionJudge[0]){
			// 部署名が 20 文字以内で部署名略称に記入がない場合に実行
			entryform.setAffilicate_name_r(
					entryform.getAffilicate_name());
			additionJudge[2] = false;
		}
		// 部署名略称が未記入且つ部署名が 20 文字上である場合
		if (additionJudge[0] && additionJudge[2]) {
			// 該当する場合エラー
			errorJudge[3] = true;
			return errorJudge;
		}
		// 適用開始日に記入があるか確認
		if (entryform.getApply_strt_date().equals("")) {
			// 記入がない場合エラー
			errorJudge[4] = true;
			return errorJudge;
		} else {
			// ある場合 Date 型に変換
			strtDate = strChangeDate(
					entryform.getApply_strt_date());
			// 変換に失敗して null が返されているか確認
			if (strtDate == null) {
				// 返された場合エラー
				errorJudge[5] = true;
				return errorJudge;
			}
		}
		// 適用終了日に記入がある場合
		if (additionJudge[1] == false) {
			// 適用終了日をDate型に変換
			finDate = this.strChangeDate(
					entryform.getApply_fin_date());
			// 取得結果画 null の場合
			if (finDate == null) {
				// 返された場合エラー
				errorJudge[6] = true;
				return errorJudge;
			}
		}
		// 適応開始日と適用終了日に値がある場合
		if (strtDate != null && finDate != null) {
			// 適用終了日が適応開始日より未来となっている場合
			if (finDate.before(strtDate)) {
				// 適用開始日が未来日になっている場合エラー
				errorJudge[7] = true;
			}
		}
		return errorJudge;
	}

	/**
	 * 登録時のエラーに対応する文を呼び出すメソッド
	 * @param エラー識別用配列
	 * @param 部署情報DTO
	 * @return エラー確認
	 */
	public boolean entryErrorElec(
			boolean[] errorJudge,
			Bfmk07Dto bfmk07Dto) {
		// エラー識別用配列に該当するエラーIDを部署情報DTOに渡す
		for (int i = 1; i <= errorJudge.length; i++) {
			// エラー判定がtrueであるかの確認
			if (errorJudge[i - 1]) {
				// エラー判定がtrueである場合
				bfmk07Dto.setMessage("mseven00" + (i+1));
				return false;
			}
		}
		return true;
	}

	/**
	 * 削除処理を管理するメソッド
	 * @param 選択リスト
	 * @param 部署情報DTO
	 */
	public void bfmk07Delete(
			List<String> deletelist, 
			Bfmk07Dto bfmk07Dto) {
		// 削除処理
		if (repository.delete_exe(deletelist, bfmk07Dto)) {
			// 正常終了の場合
			bfmk07Dto.setMessage("mseven018");
		} else {
			// エラーの場合
			bfmk07Dto.setMessage("mseven016");
		}
	}

	/**
	 * 削除のエラー確認をするメソッド
	 * @param 選択リスト
	 * @param 部署情報DTO
	 * @return エラー確認
	 */
	public boolean[] checkDeleteError(
			List<String> deletelist, 
			Bfmk07Dto bfmk07Dto) {
		// 選択された部署IDの要素数
		int size;
		// エラー識別用配列
		boolean[] errorJudge = new boolean[8];
		
		// 選択されている項目があるか確認
		if (deletelist == null) {
			// ない場合エラー
			errorJudge[0] = true;
			// エラーIDを取得する
			return errorJudge;
		} else {
			// ある場合は選択している数を取得
			size = deletelist.size();
		}
		// 選択されている項目が1ページ分以上でないか確認
		if (size >= 8) {
			// ある場合はエラーを取得
			errorJudge[1] = true;
			return errorJudge;
		}
		// 存在しないデータが対象になっていないか確認
		if (repository.deleteCheck(deletelist)) {
			// ない場合は選択している数を取得
			errorJudge[2] = true;
			return errorJudge;
		}
		// 削除対象の部署に社員がいるか確認
		boolean[] person = repository.employeesCheck(deletelist);
		for (int i = 0; i < size; i++) {
			// 〇番目に選択された部署に社員がいる場合
			if (person[i]) {
				// 操作ユーザーが admin であるか確認
				if (bfmk07Dto.getUserId().equals("admin")) {		// 他ページでログイン時に取得したユーザーIDから検索する
					// 所属している社員の削除処理の確認
					if (repository.deleteEmployees(
							deletelist.get(i), bfmk07Dto)) {
						// ユーザー情報に関する処理中にエラーが発生した場合のエラー判定
						errorJudge[3] = true;
					}
				} else {
					// adminではない場合
					errorJudge[4] = true;
					break;
				}
			}
		}
		return errorJudge;
	}

	/**
	 * 削除時のエラーに対応する文を呼び出すメソッド
	 * @param エラー識別用配列
	 * @param 部署情報DTO
	 * @return エラー確認
	 */
	public boolean deleteErrorElec(
			boolean[] errorJudge, 
			Bfmk07Dto bfmk07Dto) {
		for (int i = 1; i <= errorJudge.length; i++) {
			// エラーマップに対応する番号のエラーIDをdtoクラスに送る
			if (errorJudge[i - 1]) {
				if (i == (errorJudge.length - 1)) {
					bfmk07Dto.setMessage("mseven01" + (i - 1));
				} else {
					bfmk07Dto.setMessage("mseven01" + (i - 1));
				}
				// エラーIDを取得した時点で終了する
				return false;
			}
		}
		return true;
	}

	/**
	 * String 型の日時を Date 型に変換するメソッド
	 * @param String 型日付
	 * @return Date 型日付
	 */
	public Date strChangeDate(
			String beforeDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			// 日付のフォーマット設定
			date = format.parse(beforeDate);
		} catch (ParseException e) {
			date = null;
		}
		return date;
	}

	/**
	 * ページ数、データ合計数を部署情報DTOに渡すメソッド
	 * @param 部署情報DTO
	 */
	public void pageDetail(Bfmk07Dto bfmk07Dto) {
		// リポジトリクラスからメソッドを呼び出す。
		int[] count = repository.pageDetail();
		// 端数が0の場合最大ページから-1する
		if (count[2] == 0) {
			count[1] = count[1]-1;
		}
		bfmk07Dto.setListDataSum(count[0]);
		bfmk07Dto.setMaxPage(count[1]);
	}

	/**
	 * 操作権限に関する真偽値を返すメソッド
	 * @param 部署情報DTO
	 * @return 操作権限
	 */
	public boolean opeAuthority(Bfmk07Dto bfmk07Dto) {
		// 判定に必要な部署IDと操作権限フラグを取得
		String[] act = repository.autConf(bfmk07Dto);
		int auth = Integer.parseInt(act[1]);
		String depart = "us1"; 			// 本来は総務部で検索し部署IDを取得する
		// 部署IDが総務部のIDであるか確認
		if (act[0].equals(depart)) {
			// 操作権限フラグが2以上であるか確認
			if (2 <= auth) {
				// 権限がある場合非活性にするコードを非活性にする
				return false;
			}
		// 操作権限フラグが4,adminであるか確認
		} else if (4 == auth) {
			// 権限がある場合非活性にするコードを非活性にする
			return false;
		}
		bfmk07Dto.setMessage("mseven001");
		// 権限がある場合非活性にするコードを活性にする
		return true;
	}

	/**
	 * ページが0でない場合1引くメソッド
	 * @param 部署情報DTO
	 */
	public void previous(Bfmk07Dto bfmk07Dto) {
		// 現行ページが0であるかの確認
		if (bfmk07Dto.getPage() != 0) {
			// 0ではない場合の処理
			bfmk07Dto.setPage(bfmk07Dto.getPage() - 1);
		}
	}

	/**
	 * ページが等号でない場合に+1するメソッド
	 * @param 部署情報DTO 
	 */
	public void following(Bfmk07Dto bfmk07Dto) {
		// 現行ページが最大ページと同じ数値であるかの確認
		if (bfmk07Dto.getMaxPage() != bfmk07Dto.getPage()) {
			// 同じではない場合の処理
			bfmk07Dto.setPage(bfmk07Dto.getPage() + 1);
		}
	}

	/**
	 * ページが等号でない場合に最大ページの数を返すメソッド
	 * @param 部署情報DTO
	 */
	public void last(Bfmk07Dto bfmk07Dto) {
		// 現行ページが最大ページと同じ数値であるかの確認
		if (bfmk07Dto.getMaxPage() != bfmk07Dto.getPage()) {
			// 同じではない場合の処理
			bfmk07Dto.setPage(bfmk07Dto.getMaxPage());
		}
	}
}