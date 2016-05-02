package pokerBase;

import pokerEnums.eCardCount;
import pokerEnums.eCardDestination;
import pokerEnums.eCardVisibility;


public class CardDraw {

	private eCardCount CardCount;
	private eCardDestination CardDestination;
	private eCardVisibility CardVisibility;
	private eCardCount CardCountDrawn;
	
	public CardDraw(eCardCount cardCount, eCardDestination cardDestination, eCardVisibility cardVisiblity) {
		super();
		CardCount = cardCount;
		CardDestination = cardDestination;
		CardVisibility = CardVisibility;
	}
	public eCardCount getCardCount() {
		return CardCount;
	}
	public eCardDestination getCardDestination() {
		return CardDestination;
	}
	public eCardVisibility getCardVisibility() {
		return CardVisibility;
	}
	public eCardCount getCardCountDrawn() {
		return CardCountDrawn;
	}
	public void setCardCountDrawn(eCardCount cardCountDrawn) {
		CardCountDrawn = cardCountDrawn;
	}
	
	
	
}
