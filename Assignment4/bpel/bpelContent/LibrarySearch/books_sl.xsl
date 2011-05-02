<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<xsl:for-each select="Book">
			<Book>
				<author><xsl:value-of select="author" /></author>
				<isbn><xsl:value-of select="isbn" /></isbn>
				<pages></pages>
				<publicationDate><xsl:value-of select="year" /></publicationDate>
				<publisher><xsl:value-of select="publisher" /></publisher>
				<review></review>
				<summary></summary>
				<title><xsl:value-of select="title" /></title>
			</Book>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>
