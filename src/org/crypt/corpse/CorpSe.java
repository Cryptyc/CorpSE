package org.crypt.corpse;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import com.beimin.eveapi.EveApi;
import com.beimin.eveapi.account.characters.EveCharacter;
import com.beimin.eveapi.core.ApiAuthorization;
import com.beimin.eveapi.core.ApiException;
import com.beimin.eveapi.corporation.accountbalance.AccountBalanceParser;
import com.beimin.eveapi.corporation.assetlist.AssetListParser;
import com.beimin.eveapi.corporation.industryjobs.IndustryJobsParser;
import com.beimin.eveapi.corporation.standings.StandingsParser;
import com.beimin.eveapi.corporation.wallet.journal.WalletJournalParser;
import com.beimin.eveapi.corporation.wallet.transactions.WalletTransactionsParser;
import com.beimin.eveapi.shared.accountbalance.AccountBalanceResponse;
import com.beimin.eveapi.shared.accountbalance.EveAccountBalance;
import com.beimin.eveapi.shared.assetlist.AbstractAssetListParser;
import com.beimin.eveapi.shared.assetlist.AssetListResponse;
import com.beimin.eveapi.shared.assetlist.EveAsset;
import com.beimin.eveapi.shared.industryjobs.AbstractIndustryJobsParser;
import com.beimin.eveapi.shared.industryjobs.ApiIndustryJob;
import com.beimin.eveapi.shared.industryjobs.IndustryJobsResponse;
import com.beimin.eveapi.shared.standings.AbstractStandingsParser;
import com.beimin.eveapi.shared.standings.ApiStanding;
import com.beimin.eveapi.shared.standings.StandingsList;
import com.beimin.eveapi.shared.standings.StandingsResponse;
import com.beimin.eveapi.shared.wallet.journal.AbstractWalletJournalParser;
import com.beimin.eveapi.shared.wallet.journal.ApiJournalEntry;
import com.beimin.eveapi.shared.wallet.journal.WalletJournalResponse;
import com.beimin.eveapi.shared.wallet.transactions.AbstractWalletTransactionsParser;
import com.beimin.eveapi.shared.wallet.transactions.ApiWalletTransaction;
import com.beimin.eveapi.shared.wallet.transactions.WalletTransactionsResponse;



public class CorpSe {
	private static EveApi api = null;
	private final static String vCode = "";
	private final static Integer keyId = 0;
	private final static Integer accountId = 0;

	public static void main(String[] args) throws ApiException {

		Connection conn =  new DbInit().getDb();
		ApiAuthorization auth = new ApiAuthorization(keyId, vCode);
		api = new EveApi(auth);
		Set<EveCharacter> characters = api.getCharacters();
		EveCharacter character = characters.iterator().next();
		System.out.println("Found "+characters.size()+" characters:");
		System.out.println("character: "+character.getName()+"\t\t"+character.getCorporationName() + "\t\t" + character.getCharacterID());
		api.selectCharacter(character.getCharacterID());
		AccountBalance(auth);
		showWallet(auth, 1000);
		showTransactions(auth, 1000);
//		getAssets(auth, conn);
//		showStadings(auth);
		showJobs(auth, conn);
	}
	
	private static void getAssets(ApiAuthorization auth, Connection conn) {
		try {
			AbstractAssetListParser parser = AssetListParser.getInstance();
			AssetListResponse response = parser.getResponse(auth);
			Collection<EveAsset<EveAsset<?>>> assets = response.getAll();
			for (EveAsset asset : assets) {
				System.out.println(DbQuerys.getNamefromId(asset.getTypeID(), conn) + " is at " + asset.getLocationID());
				Collection<EveAsset> subAssets = asset.getAssets();
				for (EveAsset subasset : subAssets) {
					System.out.println("Subasset " + DbQuerys.getNamefromId(subasset.getTypeID(), conn) + " is at " + subasset.getLocationID());
				}
			}

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void AccountBalance(ApiAuthorization auth) {
		AccountBalanceParser parser = AccountBalanceParser.getInstance();
		AccountBalanceResponse response;
		try {
			response = parser.getResponse(auth);
			Set<EveAccountBalance> accountBalances = response.getAll();
			DecimalFormat formatter = new DecimalFormat("#,###.00");
			for (EveAccountBalance accountBalance : accountBalances) {
				System.out.println(accountBalance.getAccountKey() + " " + accountBalance.getAccountID() + " balace: " + formatter.format(accountBalance.getBalance()));
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void showTransactions(ApiAuthorization auth, int acId) {
		AbstractWalletJournalParser parser = WalletJournalParser.getInstance();
		if(parser == null) {
			System.out.println("parser null");
			return;
		}
		try {
			WalletJournalResponse response = parser.getResponse(auth, acId );
			if(response == null) {
				System.out.println("response null");
				return;
			}
			Collection<ApiJournalEntry> entries = response.getAll();
			System.out.println("got " + entries.size() + " entries from account " + acId);
			DecimalFormat formatter = new DecimalFormat("#,###.00");
			for (ApiJournalEntry journalEntry : entries) {
				System.out.println("from " + journalEntry.getOwnerName1() + " reason: " + journalEntry.getOwnerName2() + "  ammt: " + formatter.format(journalEntry.getAmount()));
			}

		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void showWallet(ApiAuthorization auth,  int acId) {
		AbstractWalletTransactionsParser parser = WalletTransactionsParser.getInstance();
		WalletTransactionsResponse response;
		try {
			response = parser.getResponse(auth, 1000);
			Set<ApiWalletTransaction> walletTransactions = response.getAll();
			DecimalFormat formatter = new DecimalFormat("#,###.00");
			for (ApiWalletTransaction walletTransaction : walletTransactions) {
				System.out.println(walletTransaction.getQuantity() + " " + walletTransaction.getTypeName() + " " + formatter.format(walletTransaction.getPrice()));
			}

		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static void showStadings(ApiAuthorization auth) {
		StandingsParser parser = StandingsParser.getInstance();
		try {
			StandingsResponse response = parser.getResponse(auth);
			StandingsList factionStandings = response.getFactionStandings();
			Iterator<ApiStanding> factionIter = factionStandings.iterator();
			while (factionIter.hasNext()) {
				ApiStanding thisstand = factionIter.next();
				System.out.println("Standings for " + thisstand.getFromName() + ": " + thisstand.getStanding());
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}
	
	private static void showJobs(ApiAuthorization auth, Connection conn) {
		AbstractIndustryJobsParser parser = IndustryJobsParser.getInstance();
		IndustryJobsResponse response;
		int completed = 0;
		try {
			response = parser.getResponse(auth);
			Collection<ApiIndustryJob> industryJobs = response.getAll();
			for (ApiIndustryJob job : industryJobs) {
				
				if(job.getEndProductionTime().after(new Date())) {
					System.out.println("Item: " + DbQuerys.getNamefromId(job.getInstalledItemTypeID(), conn) + " Finishes at : " + job.getEndProductionTime());
				} else {
					completed ++;
				}
			}
			System.out.println("Completed " + completed + " Jobs");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}