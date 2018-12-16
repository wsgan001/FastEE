/* Copyright (C) 2018 Chang Wei Tan, Francois Petitjean, Geoff Webb
 This file is part of FastEE.
 FastEE is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, version 3 of the License.
 LbEnhanced is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 You should have received a copy of the GNU General Public License
 along with LbEnhanced.  If not, see <http://www.gnu.org/licenses/>. */
package timeseriesweka.lowerBounds;

import timeseriesweka.fastWWS.SequenceStatsCache;
import weka.core.Instance;

/**
 * Code for the paper "FastEE: Fast Ensembles of Elastic Distances for Time Series Classification"
 *
 * @author Chang Wei Tan, Francois Petitjean, Geoff Webb
 *
 * Lower bound for DTW (LB Kim)
 */
public class LbKim {
    public static double distance(final Instance query, final Instance reference,
                                  final SequenceStatsCache queryCache, final SequenceStatsCache referenceCache,
                                  final int indexQuery, final int indexReference) {
        final double diffFirsts = query.value(0) - reference.value(0);
        final double diffLasts = query.value(query.numAttributes() - 2) - reference.value(reference.numAttributes() - 2);
        double minDist = diffFirsts * diffFirsts + diffLasts * diffLasts;

        if (!queryCache.isMinFirst(indexQuery) && !referenceCache.isMinFirst(indexReference) &&
                !queryCache.isMinLast(indexQuery) && !referenceCache.isMinLast(indexReference)) {
            final double diffMin = queryCache.getMin(indexQuery) - referenceCache.getMin(indexReference);
            minDist += diffMin * diffMin;
        }
        if (!queryCache.isMaxFirst(indexQuery) && !referenceCache.isMaxFirst(indexReference) &&
                !queryCache.isMaxLast(indexQuery) && !referenceCache.isMaxLast(indexReference)) {
            final double diffMax = queryCache.getMax(indexQuery) - referenceCache.getMax(indexReference);
            minDist += diffMax * diffMax;
        }

        return minDist;
    }

    public static double distance(final Instance query, final Instance reference, final SequenceStatsCache cache,
                                  final int indexQuery, final int indexReference) {
        final double diffFirsts = query.value(0) - reference.value(0);
        final double diffLasts = query.value(query.numAttributes() - 2) - reference.value(reference.numAttributes() - 2);
        double minDist = diffFirsts + diffLasts;

        if (!cache.isMinFirst(indexQuery) && !cache.isMinFirst(indexReference) &&
                !cache.isMinLast(indexQuery) && !cache.isMinLast(indexReference)) {
            final double diffMin = cache.getMin(indexQuery) - cache.getMin(indexReference);
            minDist += diffMin * diffMin;
        }
        if (!cache.isMaxFirst(indexQuery) && !cache.isMaxFirst(indexReference) &&
                !cache.isMaxLast(indexQuery) && !cache.isMaxLast(indexReference)) {
            final double diffMax = cache.getMax(indexQuery) - cache.getMax(indexReference);
            minDist += diffMax * diffMax;
        }

        return minDist;
    }
}
