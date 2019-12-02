<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- =========================================== -->
<!-- File    : REM_SpecificationObject.xsl       -->
<!-- Content : Stylesheet for Spec. Objects      -->
<!-- Author  : Amador Duran Toro                 -->
<!-- Date    : 2001/01/30                        -->
<!-- Version : 1.2                               -->
<!-- =========================================== -->
<!-- Last update : 2002/07/14                    -->
<!-- Authors/Sources use <ul> now                -->
<!-- =========================================== -->
<!-- Last update : 2002/07/12                    -->
<!-- First version                               -->
<!-- =========================================== -->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================================= -->
<!-- SpecificationObject (abstract class) template -->
<!-- ============================================= -->

<xsl:template name="SpecificationObject_Top">
    <xsl:param name="span">1</xsl:param>
   
    <xsl:apply-templates select="rem:name"/>   
    <xsl:apply-templates select="rem:version"/>
    
    <xsl:call-template name="Authors">
        <xsl:with-param name="span" select="$span"/>
    </xsl:call-template>
    
    <xsl:call-template name="Sources">
        <xsl:with-param name="span" select="$span"/>
    </xsl:call-template>   
</xsl:template>

<xsl:template name="SpecificationObject_Bottom">
    <xsl:param name="span">1</xsl:param>
    
    <xsl:call-template name="GenerateRow"> 
        <xsl:with-param name="title">
			<xsl:value-of select="$rem:lang_comments"/>		
		</xsl:with-param>
        <xsl:with-param name="node" select="rem:comments"/> 
        <xsl:with-param name="span" select="$span"/>        
    </xsl:call-template>
</xsl:template>

<!-- ============================================= -->
<!-- rem:version                                   -->
<!-- ============================================= -->

<xsl:template match="rem:version">
    <tr>
		<th align="left" valign="top">
			<xsl:value-of select="$rem:lang_version"/>
		</th>
        <td align="left" valign="top">
            <xsl:value-of select="rem:major"/>.<xsl:value-of select="rem:minor"/> 
			( <xsl:apply-templates select="rem:date"/> )
        </td>
    </tr>
</xsl:template>

<!-- ============================================= -->
<!-- Authors/Sources template                      -->
<!-- ============================================= -->

<xsl:template name="Authors">
    <xsl:param name="span">1</xsl:param>

    <tr>
        <th align="left" valign="top">
			<xsl:value-of select="$rem:lang_authors"/>
		</th>
        <td align="left" valign="top">
        <xsl:attribute name="colspan">
            <xsl:value-of select="$span"/>
        </xsl:attribute>
        <xsl:choose>
            <xsl:when test="not(rem:authors)">
                <font color="red"><b>?</b></font>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates select="rem:authors"/>
            </xsl:otherwise>
        </xsl:choose>
        </td>
    </tr>
</xsl:template>

<xsl:template name="Sources">
    <xsl:param name="span">1</xsl:param>

    <tr>
        <th align="left" valign="top">
			<xsl:value-of select="$rem:lang_sources"/>
		</th>
        <td align="left" valign="top">
        <xsl:attribute name="colspan">
            <xsl:value-of select="$span"/>
        </xsl:attribute>
        <xsl:choose>
            <xsl:when test="not(rem:sources)">
                <font color="red"><b>?</b></font>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates select="rem:sources"/>
            </xsl:otherwise>
        </xsl:choose>
        </td>
    </tr>
</xsl:template>

<!-- ============================================== -->
<!-- rem:authors|rem:sources template               -->
<!-- ============================================== -->

<xsl:template match="rem:authors|rem:sources">
	<ul>
        <xsl:for-each select="id(@stakeholders)">
        <xsl:sort select="rem:name"/>
            <li>
				<a>
					<xsl:attribute name="href">#<xsl:value-of select="@oid"/></xsl:attribute>
					<xsl:value-of select="rem:name"/>
				</a>
				<br/>
			</li>
        </xsl:for-each>
	</ul>
</xsl:template>

</xsl:stylesheet>