/*
 * Copyright (C) 2014-2015 LinkedIn Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.
 */

package gobblin.metrics;

import lombok.experimental.Delegate;

import java.util.Collection;
import java.util.List;

import com.codahale.metrics.ExponentiallyDecayingReservoir;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;

import com.google.common.base.Optional;

import gobblin.metrics.metric.TrueMetric;


/**
 * A type of {@link com.codahale.metrics.Histogram} that are aware of their {@link gobblin.metrics.MetricContext}
 * and can have associated {@link Tag}s.
 *
 * <p>
 *   Any updates to a {@link ContextAwareHistogram} will be applied automatically to the
 *   {@link ContextAwareHistogram} of the same name in the parent {@link MetricContext}.
 * </p>
 *
 * <p>
 *   This class wraps a {@link com.codahale.metrics.Histogram} and delegates calls to public methods of
 *   {@link com.codahale.metrics.Histogram} to the wrapped {@link com.codahale.metrics.Histogram}.
 * </p>
 *
 * @author ynli
 */
class ContextAwareHistogram extends Histogram implements ContextAwareMetric {

  @Delegate
  private final TrueHistogram histogram;

  private final MetricContext context;

  ContextAwareHistogram(MetricContext context, String name) {
    super(new ExponentiallyDecayingReservoir());
    this.histogram = new TrueHistogram(context, name, this);
    this.context = context;

  }

  @Override
  public MetricContext getContext() {
    return this.context;
  }

  @Override public TrueMetric getTrueMetric() {
    return this.histogram;
  }
}
