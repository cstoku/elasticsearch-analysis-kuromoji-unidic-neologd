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

package jp.toku.elasticsearch.kuromoji.unidic.neologd.index.analysis;

import java.io.Reader;

import jp.toku.neologd.unidic.lucene.analysis.ja.JapaneseIterationMarkCharFilter;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractCharFilterFactory;
import org.elasticsearch.index.settings.IndexSettingsService;

public class KuromojiIterationMarkCharFilterFactory extends AbstractCharFilterFactory {

    private final boolean normalizeKanji;
    private final boolean normalizeKana;

    @Inject
    public KuromojiIterationMarkCharFilterFactory(Index index, IndexSettingsService indexSettingsService,
                                                  @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettingsService.getSettings(), name);
        normalizeKanji = settings.getAsBoolean("normalize_kanji", JapaneseIterationMarkCharFilter.NORMALIZE_KANJI_DEFAULT);
        normalizeKana = settings.getAsBoolean("normalize_kana", JapaneseIterationMarkCharFilter.NORMALIZE_KANA_DEFAULT);
    }

    @Override
    public Reader create(Reader reader) {
        return new JapaneseIterationMarkCharFilter(reader, normalizeKanji, normalizeKana);
    }
}
