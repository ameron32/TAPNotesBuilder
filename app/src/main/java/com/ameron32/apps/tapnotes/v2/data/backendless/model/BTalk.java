package com.ameron32.apps.tapnotes.v2.data.backendless.model;

import com.ameron32.apps.tapnotes.v2.data.model.EventType;
import com.ameron32.apps.tapnotes.v2.data.model.ITalk;
import com.ameron32.apps.tapnotes.v2.scripture.Bible;
import com.ameron32.apps.tapnotes.v2.scripture.Scripture;
import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.ameron32.apps.tapnotes.v2.data.parse.Constants.TALK_HEADERIMAGEURL_STRING_KEY;
import static com.ameron32.apps.tapnotes.v2.data.parse.Constants.TALK_TYPE_STRING_KEY;

/**
 * Created by klemeilleur on 4/25/2016.
 */
public class BTalk implements ITalk<Scripture, Bible> {
  private String type;
  private String title;
  private String scriptures;
  private String ownerId;
  private java.util.Date updated;
  private Integer durationMinutes;
  private String dateTime;
  private java.util.Date created;
  private java.util.Date updateTimedAt;
  private String sequence;
  private String metadata;
  private String objectId;
  private BProgram oProgram;

  public BTalk() {
    // required empty constructor
  }

  @Override
  public String getHeaderImageUrl() {
    // TODO database does not yet support headerImageUrl string
    return "";
  }

  @Override
  public EventType getEventType() {
    return EventType.valueOfAnyCase(getType());
  }

  @Override
  public List<Scripture> getTalkScriptures(final Bible bible) {
    return Scripture.generateAll(bible, getTalkThemeScriptures());
  }

  private String getTalkThemeScriptures() {
    return getScriptures();
  }

  @Override
  public int getDurationInMinutes() {
    return getDurationMinutes();
  }

  @Override
  public String getId() {
    return getObjectId();
  }

  @Override
  public String getTalkTitle() {
    return getTitle();
  }

  public String getType()
  {
    return type;
  }

  public void setType( String type )
  {
    this.type = type;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle( String title )
  {
    this.title = title;
  }

  public String getScriptures()
  {
    return scriptures;
  }

  public void setScriptures( String scriptures )
  {
    this.scriptures = scriptures;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }

  public Integer getDurationMinutes()
  {
    return durationMinutes;
  }

  public void setDurationMinutes( Integer durationMinutes )
  {
    this.durationMinutes = durationMinutes;
  }

  public String getDateTime()
  {
    return dateTime;
  }

  @Override
  public DateTime getDateAndTime(Locale locale) {
    final DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy H:mm")
        .withLocale(locale);
    return DateTime.parse(getDateTime(), formatter);
  }

  public void setDateTime(String dateTime )
  {
    this.dateTime = dateTime;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public java.util.Date getUpdateTimedAt()
  {
    return updateTimedAt;
  }

  public void setUpdateTimedAt( java.util.Date updateTimedAt )
  {
    this.updateTimedAt = updateTimedAt;
  }

  public String getSequence()
  {
    return sequence;
  }

  public void setSequence( String sequence )
  {
    this.sequence = sequence;
  }

  public String getMetadata()
  {
    return metadata;
  }

  public String getSymposiumTitle() {
    if (getEventType() == EventType.SYMPOSIUMTALK) {
      return getMetadata();
    }
    return "";
  }

  @Override
  public int getSongNumber() {
    if (getEventType() == EventType.SONG) {
      return getSongNumberWithinMetadata();
    } else {
      return NO_SONG_NUMBER;
    }
  }

  private int getSongNumberWithinMetadata() {
    return Integer.valueOf(getMetadata().substring(1));
  }

  public void setMetadata(String metadata )
  {
    this.metadata = metadata;
  }

  public String getObjectId()
  {
    return objectId;
  }

  public BProgram getOProgram()
  {
    return oProgram;
  }

  public void setOProgram( BProgram oProgram )
  {
    this.oProgram = oProgram;
  }

  public BTalk save()
  {
    return Backendless.Data.of( BTalk.class ).save( this );
  }

  public Long remove()
  {
    return Backendless.Data.of( BTalk.class ).remove( this );
  }

  public static BTalk findById( String id )
  {
    return Backendless.Data.of( BTalk.class ).findById( id );
  }

  public static BTalk findFirst()
  {
    return Backendless.Data.of( BTalk.class ).findFirst();
  }

  public static BTalk findLast()
  {
    return Backendless.Data.of( BTalk.class ).findLast();
  }

  public static BackendlessCollection<BTalk> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( BTalk.class ).find( query );
  }

  @Override
  public void setUserTimestamp(Date date) {
    // TODO ignore userTimestamp for Backendless
  }

}