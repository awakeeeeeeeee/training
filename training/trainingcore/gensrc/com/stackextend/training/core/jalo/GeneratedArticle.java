/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 2021-12-8 17:35:42                          ---
 * ----------------------------------------------------------------
 */
package com.stackextend.training.core.jalo;

import com.stackextend.training.core.constants.TrainingCoreConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem Article}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedArticle extends GenericItem
{
	/** Qualifier of the <code>Article.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>Article.name</code> attribute **/
	public static final String NAME = "name";
	/** Qualifier of the <code>Article.author</code> attribute **/
	public static final String AUTHOR = "author";
	/** Qualifier of the <code>Article.status</code> attribute **/
	public static final String STATUS = "status";
	/** Qualifier of the <code>Article.content</code> attribute **/
	public static final String CONTENT = "content";
	/** Qualifier of the <code>Article.previewCount</code> attribute **/
	public static final String PREVIEWCOUNT = "previewCount";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(NAME, AttributeMode.INITIAL);
		tmp.put(AUTHOR, AttributeMode.INITIAL);
		tmp.put(STATUS, AttributeMode.INITIAL);
		tmp.put(CONTENT, AttributeMode.INITIAL);
		tmp.put(PREVIEWCOUNT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.author</code> attribute.
	 * @return the author
	 */
	public String getAuthor(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AUTHOR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.author</code> attribute.
	 * @return the author
	 */
	public String getAuthor()
	{
		return getAuthor( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.author</code> attribute. 
	 * @param value the author
	 */
	public void setAuthor(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AUTHOR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.author</code> attribute. 
	 * @param value the author
	 */
	public void setAuthor(final String value)
	{
		setAuthor( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.content</code> attribute.
	 * @return the content
	 */
	public String getContent(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CONTENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.content</code> attribute.
	 * @return the content
	 */
	public String getContent()
	{
		return getContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.content</code> attribute. 
	 * @param value the content
	 */
	public void setContent(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.content</code> attribute. 
	 * @param value the content
	 */
	public void setContent(final String value)
	{
		setContent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.name</code> attribute.
	 * @return the name
	 */
	public String getName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.name</code> attribute.
	 * @return the name
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final String value)
	{
		setName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.previewCount</code> attribute.
	 * @return the previewCount
	 */
	public Integer getPreviewCount(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, PREVIEWCOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.previewCount</code> attribute.
	 * @return the previewCount
	 */
	public Integer getPreviewCount()
	{
		return getPreviewCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.previewCount</code> attribute. 
	 * @return the previewCount
	 */
	public int getPreviewCountAsPrimitive(final SessionContext ctx)
	{
		Integer value = getPreviewCount( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.previewCount</code> attribute. 
	 * @return the previewCount
	 */
	public int getPreviewCountAsPrimitive()
	{
		return getPreviewCountAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.previewCount</code> attribute. 
	 * @param value the previewCount
	 */
	public void setPreviewCount(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, PREVIEWCOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.previewCount</code> attribute. 
	 * @param value the previewCount
	 */
	public void setPreviewCount(final Integer value)
	{
		setPreviewCount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.previewCount</code> attribute. 
	 * @param value the previewCount
	 */
	public void setPreviewCount(final SessionContext ctx, final int value)
	{
		setPreviewCount( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.previewCount</code> attribute. 
	 * @param value the previewCount
	 */
	public void setPreviewCount(final int value)
	{
		setPreviewCount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.status</code> attribute.
	 * @return the status
	 */
	public EnumerationValue getStatus(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, STATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Article.status</code> attribute.
	 * @return the status
	 */
	public EnumerationValue getStatus()
	{
		return getStatus( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, STATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Article.status</code> attribute. 
	 * @param value the status
	 */
	public void setStatus(final EnumerationValue value)
	{
		setStatus( getSession().getSessionContext(), value );
	}
	
}
