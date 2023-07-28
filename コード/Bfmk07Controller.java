package com.seizou.kojo.domain.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seizou.kojo.domain.dto.Bfmk07Dto;
import com.seizou.kojo.domain.form.Bfmk07DeleteForm;
import com.seizou.kojo.domain.form.Bfmk07EntryForm;
import com.seizou.kojo.domain.service.Bfmk07Service;

/**
 * 部署情報のControllerクラス
 * @author b-forme_Kajiura
 *
 */
@Controller
@RequestMapping("/b-forme_Kojo")
public class Bfmk07Controller {
	/** 部署情報サービスクラス */
	@Autowired
	Bfmk07Service bfmk07service;
	/** 部署情報Dto */
	@Autowired
	Bfmk07Dto bfmk07Dto;
	/** メッセージ */
	@Autowired
	MessageSource messages;

	
	/**
	 * 初期画面
	 * @param モデル
	 * @return 画面
	 */
	@GetMapping(value = "/pc/207")
	public String initial(Model model) {
		// 部署情報DTOの初期化
		bfmk07Dto = new Bfmk07Dto();

		// 共通処理
		this.setting(model);

		return "Bfmk07View";
	}

	/**
	 * 登録処理
	 * @param 入力フォーム
	 * @param モデル
	 * @return 画面
	 */
	@PostMapping(value = "/pc/207", params = "entry")
	public String entry(
			Bfmk07EntryForm entryform,
			Model model) {
		// 取得した入力フォームの異常確認
		if (bfmk07service.entryErrorElec(
				bfmk07service.checkEntryError(
						entryform),
				bfmk07Dto)) {
			//異常がない場合登録実行する
			bfmk07service.bfmk07Insert(entryform,
					bfmk07Dto);
		}
		// 共通処理
		this.setting(model);

		return "Bfmk07View";
	}

	/**
	 * 削除処理
	 * @param 選択フォーム
	 * @param モデル
	 * @return 画面
	 */
	@PostMapping(value = "/pc/207", params = "delete")
	public String delete(
			Bfmk07DeleteForm deleteform,
			Model model) {
		// 取得した選択フォームの異常確認
		if (bfmk07service.deleteErrorElec(
				bfmk07service.checkDeleteError(
					deleteform.getDeletelist(), bfmk07Dto),bfmk07Dto)
				) {
			bfmk07service.bfmk07Delete(
					//異常がない場合削除処理を実行する
					deleteform.getDeletelist(),bfmk07Dto);
		}
		// 共通処理
		this.setting(model);

		return "Bfmk07View";
	}
	
	/**
	 * 戻る
	 * @return 画面
	 */
	@PostMapping(value = "/pc/207", params = "back")
	public String backPage() {
		return "bfkt02View";
	}

	/**
	 * クリア
	 * @param モデル
	 * @return 画面
	 */
	@PostMapping(value = "/pc/207", params = "clear")
	public String clear(Model model) {
		// ページ数、メッセージの初期化
		bfmk07Dto = new Bfmk07Dto();
		
		// 共通処理
		this.setting(model);

		return "Bfmk07View";
	}

	/**
	 * １つ前のページに遷移
	 * @param モデル
	 * @return 画面
	 */
	@PostMapping(value = "/pc/207", params = "previous")
	public String previousPage(Model model) {
		// ページ数を更新
		bfmk07service.previous(bfmk07Dto);
		// 共通処理
		this.setting(model);

		return "Bfmk07View";
	}

	/**
	 * 最初のページに遷移
	 * @param モデル
	 * @return 画面
	 */
	@PostMapping(value = "/pc/207", params = "first")
	public String firstPage(Model model) {
		// ページ数を更新
		if (bfmk07Dto.getPage() != 0) {
			bfmk07Dto.setPage(0);
		}
		// 共通処理
		this.setting(model);

		return "Bfmk07View";
	}

	/**
	 * 最終ページに遷移
	 * @param モデル
	 * @return 画面
	 */
	@PostMapping(value = "/pc/207", params = "last")
	public String lastPage(Model model) {
		// ページ数を更新
		bfmk07service.last(bfmk07Dto);
		// 共通処理
		this.setting(model);

		return "Bfmk07View";
	}

	/**
	 * 次のページに遷移
	 * @param モデル
	 * @return 画面
	 */
	@PostMapping(value = "/pc/207", params = "following")
	public String followingPage(Model model) {
		// ページ数を更新
		bfmk07service.following(bfmk07Dto);
		// 共通処理
		this.setting(model);

		return "Bfmk07View";
	}

	/**
	 * 共通処理
	 * @param モデル
	 */
	private void setting(Model model) {
		
		// 工場コードを設定
		bfmk07Dto.setFacCd("bfm1");
		// 部署ID
		if (bfmk07Dto.getAffId() == null || "".equals(bfmk07Dto.getAffId())) {
			bfmk07Dto.setAffId("1234");
		}
		// 部署名
		if (bfmk07Dto.getAffName() == null || "".equals(bfmk07Dto.getAffName())) {
			bfmk07Dto.setAffName("1234");
		}
		// ユーザーID
		if (bfmk07Dto.getUserId() == null || "".equals(bfmk07Dto.getUserId())) {
			bfmk07Dto.setUserId("al00000");
		}
		// ユーザー名
		if (bfmk07Dto.getUserName() == null || "".equals(bfmk07Dto.getUserName())) {
			bfmk07Dto.setUserName("1234");
		}
		
		// 画面に値を設定
		model.addAttribute(
				"autFlg", bfmk07service.opeAuthority(bfmk07Dto));			// 操作権限
		bfmk07service.pageDetail(bfmk07Dto);
		model.addAttribute(
				"Bfmk07List", bfmk07service.pagination(bfmk07Dto));			// 部署情報リスト
		if (bfmk07Dto.getMessage() != null) {
		model.addAttribute("message",messages.getMessage(
						bfmk07Dto.getMessage(), null, Locale.JAPAN));		// メッセージ
		}
		model.addAttribute(
				"nowPage",bfmk07Dto.getPage()+1);							// 現行ページ
		model.addAttribute(
				"maxPage",bfmk07Dto.getMaxPage()+1);						// 最大ページ
		model.addAttribute(
				"maxNumber",bfmk07Dto.getListDataSum());					// リストの件数
	}

	/**
	 * 入力フォームを初期化
	 * @return 入力フォーム
	 */
	@ModelAttribute("entryform")
	public Bfmk07EntryForm entry() {
		return new Bfmk07EntryForm();
	}

	/**
	 * 選択フォームを初期化
	 * @return 選択フォーム
	 */
	@ModelAttribute("deleteform")
	public Bfmk07DeleteForm delete() {
		return new Bfmk07DeleteForm();
	}
}