package com.seizou.kojo.domain.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bfmk07Dto {
	
	private String facCd; 					// 工場コード		
	private String message;					// メッセージ
	private String affId;					// 所属ID
	private String affName;					// 所属名
	private String userId;					// ユーザーID
	private String userName;				// ユーザー名
	private int page;						// 現行ページ
	private Integer listDataSum;			// 部署データ合計
	private Integer maxPage;				// 部署ページ最大数
	
}
