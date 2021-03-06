/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.cstoku.elasticsearch.kuromoji.unidic.neologd.index.analysis;

import com.github.cstoku.neologd.unidic.lucene.analysis.ja.JapaneseAnalyzer;
import com.github.cstoku.neologd.unidic.lucene.analysis.ja.JapaneseTokenizer;
import com.github.cstoku.neologd.unidic.lucene.analysis.ja.dict.UserDictionary;
import org.apache.lucene.analysis.util.CharArraySet;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.analysis.Analysis;
import org.elasticsearch.index.settings.IndexSettingsService;

import java.util.Set;

/**
 */
public class KuromojiAnalyzerProvider extends AbstractIndexAnalyzerProvider<JapaneseAnalyzer> {

    private final JapaneseAnalyzer analyzer;

    @Inject
    public KuromojiAnalyzerProvider(Index index, IndexSettingsService indexSettingsService, Environment env, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettingsService.getSettings(), name, settings);
        final Set<?> stopWords = Analysis.parseStopWords(env, settings, JapaneseAnalyzer.getDefaultStopSet());
        final JapaneseTokenizer.Mode mode = KuromojiTokenizerFactory.getMode(settings);
        final UserDictionary userDictionary = KuromojiTokenizerFactory.getUserDictionary(env, settings);
        analyzer = new JapaneseAnalyzer(userDictionary, mode, CharArraySet.copy(stopWords), JapaneseAnalyzer.getDefaultStopTags());
    }

    @Override
    public JapaneseAnalyzer get() {
        return this.analyzer;
    }


}
