---
id: overview_opencensus
title: "OpenCensus"
---

OpenCensus is a stats collection and distributed tracing framework.

## Installation

First, add the following dependency to your build.sbt:
```
"dev.zio" %% "zio-opencensus" % <version>
```

## Usage

After importing `import zio.telemetry.opencensus._`, additional combinators
on `ZIO`s are available to support starting child spans and adding attributes.

```scala
// start a new root span and set some attributes
val zio = UIO.unit
             .withAttributes(("foo", AttributeValue.stringAttributeValue("bar")))
             .root("root span")
```

To propagate contexts across process boundaries, extraction and injection can be
used. The current span context is injected into a carrier, which is passed
through some side channel to the next process. There it is injected back and a
child span of it is started.

Due to the use of the (mutable) OpenCensus carrier APIs, injection and extraction
are not referentially transparent.