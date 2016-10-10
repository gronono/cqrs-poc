package poc.demo.lucene;

import java.io.Reader;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.codec.language.bm.Languages.LanguageSet;
import org.apache.commons.codec.language.bm.NameType;
import org.apache.commons.codec.language.bm.PhoneticEngine;
import org.apache.commons.codec.language.bm.RuleType;
import org.apache.lucene.analysis.ASCIIFoldingFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.fr.ElisionFilter;
import org.apache.lucene.analysis.phonetic.BeiderMorseFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;
import org.tartarus.snowball.ext.FrenchStemmer;

/**
 * Implémentation de Analyser évitant la déclaration par annotation et donc une meilleur réutilisabilité.
 */
// final requis par Lucene
public final class ApproximatifAnalyser extends Analyzer {

	private static final Version LUCENE_VERSION = Version.LUCENE_36;

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		TokenStream stream = new StandardTokenizer(LUCENE_VERSION, reader);
		
		// On ignore les majuscules
		stream = new LowerCaseFilter(LUCENE_VERSION, stream);
		
		// On ignore les accents en convertisant les caractères non ASCII en ASCII
		stream = new ASCIIFoldingFilter(stream);
		
		// On tient compte des elisions: l', d', c', ...
		stream = new ElisionFilter(LUCENE_VERSION, stream);

		// On indique qu'on utilise le français
		stream = new SnowballFilter(stream, new FrenchStemmer());
		
		// On fait une indexation phonétique
		stream = new BeiderMorseFilter(
				stream, 
				new PhoneticEngine(NameType.GENERIC, RuleType.APPROX, true), 
				LanguageSet.from(new HashSet<>(Arrays.asList("french"))));
		
		return stream; 
	}

}
