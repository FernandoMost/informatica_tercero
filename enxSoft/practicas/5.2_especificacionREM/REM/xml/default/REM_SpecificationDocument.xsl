<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- ======================================= -->
<!-- File    : REM_SpecificationDocument.xsl -->
<!-- Content : Stylesheet for REM Documents  -->
<!-- Author  : Amador Durán Toro             -->
<!-- Date    : 2001/01/30                    -->
<!-- Version : 1.0                           -->
<!-- ======================================= -->
<!-- Last update : 2002/07/14                -->
<!-- TOC has its own HTML class              -->
<!-- ======================================= -->
<!-- Last update : 2002/07/10                -->
<!-- Embedded styles                         -->
<!-- ======================================= -->
<!-- Last update : 2002/05/10                -->
<!-- Namespace URI has changed               -->
<!-- ======================================= -->
<!-- Last update : 2004/09/19                -->
<!-- <a> tags now use title attribute        -->
<!-- ======================================= -->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================================= -->
<!-- Specification Document template               -->
<!-- ============================================= -->

<xsl:template match="rem:c_requirementsSpecification|rem:d_requirementsSpecification|rem:defectsSpecification|rem:changeRequestsSpecification">

    <!-- Cover -->

    <!-- Title -->

    <html>

    <head>
        <title>
           <xsl:value-of select="$rem:lang_project"/><xsl:text> </xsl:text><xsl:value-of select="../rem:name"/>
        </title>

        <style type="text/css">
        <xsl:comment>
            body {
                font-family: arial,verdana;
                font-size: 10pt;
                text-align: justify
            }

            h1 { font-size: 14pt }
            h2 { font-size: 13pt }
            h3 { font-size: 12pt }
            h4 { font-size: 11pt }
            h5 { font-size: 10pt }
            h6 { font-size: 10pt }

            h1.cover { font-size: 20pt }

            tr { font-size: 10pt }

            table { border: black solid; }
            table.TOC { border: none; }
            ul {
                margin-top:    0pt;
                margin-bottom: 0pt;
                margin-left:   12pt
            }
        </xsl:comment>
        </style>

    </head>

    <body>

    <center>
    <h3>
        <xsl:value-of select="$rem:lang_project"/><xsl:text> </xsl:text><xsl:value-of select="../rem:name"/>
    </h3>

    <p/>

    <h1 class="cover">
        <xsl:value-of select="rem:name"/>
    </h1>

    <p/>

    <!-- Version -->

    <h3>
        <xsl:value-of select="$rem:lang_version"/>&#32;<xsl:value-of select="rem:version/rem:major"/>.<xsl:value-of select="rem:version/rem:minor"/>
    </h3>

    <h3>
        <xsl:value-of select="$rem:lang_date"/>&#32;<xsl:apply-templates select="rem:version/rem:date"/>
    </h3>

    <p/>

    <!-- Customer organizations -->

    <h3><xsl:value-of select="$rem:lang_prepared_for"/></h3>
        <xsl:choose>
            <xsl:when test="not(rem:preparedFor)">
                <p><font color="red">?</font></p>
            </xsl:when>
            <xsl:otherwise>
            <xsl:for-each select="id(rem:preparedFor/@organizations)">
                <xsl:sort select="rem:name"/>
                <a>
                    <xsl:attribute name="href">#<xsl:value-of select="@oid"/></xsl:attribute>
                    <xsl:attribute name="title"><xsl:value-of select="normalize-space(rem:name)"/></xsl:attribute>
                    <p><xsl:value-of select="rem:name"/></p>
                </a>
            </xsl:for-each>
            </xsl:otherwise>
        </xsl:choose>
    <p/>

    <!-- Developer organizations -->

    <h3><xsl:value-of select="$rem:lang_prepared_by"/></h3>
        <xsl:choose>
            <xsl:when test="not(rem:preparedBy)">
                <p><font color="red">?</font></p>
            </xsl:when>
            <xsl:otherwise>
            <xsl:for-each select="id(rem:preparedBy/@organizations)">
                <xsl:sort select="rem:name"/>
                <a>
                    <xsl:attribute name="href">#<xsl:value-of select="@oid"/></xsl:attribute>
                    <xsl:attribute name="title"><xsl:value-of select="normalize-space(rem:name)"/></xsl:attribute>
                    <p><xsl:value-of select="rem:name"/></p>
                </a>
            </xsl:for-each>
            </xsl:otherwise>
        </xsl:choose>
    <p/>

    </center>
    <hr size="4"/>
    <p/>

    <!-- Table of contents (three first levels only) -->

    <h1 align="left"><xsl:value-of select="$rem:lang_TOC"/></h1>
    <p/>
    <table border="0" width="100%" class="TOC">
    <xsl:for-each select="rem:section[rem:level='1']">
        <tr>
            <th align="left" valign="top"><xsl:value-of select="rem:number"/></th>
            <th align="left" valign="top" width="100%" colspan="3">
                <a><xsl:attribute name="href">#<xsl:value-of select="@oid"/></xsl:attribute><xsl:value-of select="rem:name"/></a>
            </th>
        </tr>
        <xsl:for-each select=".//rem:section[rem:level='2']">
            <tr><td/>
                <td align="left" valign="top"><xsl:value-of select="rem:number"/></td>
                <td align="left" valign="top" width="100%" colspan="2">
                    <a><xsl:attribute name="href">#<xsl:value-of select="@oid"/></xsl:attribute><xsl:value-of select="rem:name"/></a>
                </td>
            </tr>
            <xsl:for-each select=".//rem:section[rem:level='3']">
                <tr><td/><td/>
                    <td albgn="left" valign="top"><xsl:value-of select="rem:number"/></td>
                    <td align="left" valign="top" width="100%">
                        <a><xsl:attribute name="href">#<xsl:value-of select="@oid"/></xsl:attribute><xsl:value-of select="rem:name"/></a>
                    </td>
                </tr>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:for-each>
    </table>
    <p/>
    <hr size="4"/>
    <p/>

    <!-- Document body -->

    <xsl:apply-templates select=
        "rem:section                  |
         rem:paragraph                |
         rem:graphicFile              |
         rem:glossaryItem             |
         rem:organization             |
         rem:stakeholder              |
         rem:meeting                  |
         rem:objective                |
         rem:actor                    |
         rem:informationRequirement   |
         rem:constraintRequirement    |
         rem:useCase                  |
         rem:functionalRequirement    |
         rem:nonFunctionalRequirement |
         rem:objectType               |
         rem:userDefinedValueType     |
         rem:associationType          |
         rem:systemOperation          |
         rem:conflict                 |
         rem:defect                   |
         rem:changeRequest            |
         rem:traceabilityMatrix"
    />

    </body>
</html>

</xsl:template>

</xsl:stylesheet>
