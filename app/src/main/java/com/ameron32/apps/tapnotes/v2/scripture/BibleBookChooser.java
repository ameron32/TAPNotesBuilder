package com.ameron32.apps.tapnotes.v2.scripture;

/**
 * Created by klemeilleur on 7/9/2015.
 */
public class BibleBookChooser {

  public static final int BOOK_NOT_FOUND = -1;

  public int determineBook(final Bible b, final String userInputBook) {

    int bookNumber = b.findBibleBook(userInputBook);
    if (bookNumber != BOOK_NOT_FOUND) {
      // found a book number, return it
      return bookNumber;
    }

    // failed
    return BOOK_NOT_FOUND;
  }
}
