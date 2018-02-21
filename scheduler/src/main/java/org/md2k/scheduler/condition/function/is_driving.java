package org.md2k.scheduler.condition.function;
/*
 * Copyright (c) 2016, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.udojava.evalex.Expression;

import org.md2k.datakitapi.datatype.DataType;
import org.md2k.datakitapi.datatype.DataTypeDoubleArray;
import org.md2k.datakitapi.datatype.DataTypeIntArray;
import org.md2k.datakitapi.source.application.ApplicationBuilder;
import org.md2k.datakitapi.source.datasource.DataSourceBuilder;
import org.md2k.datakitapi.source.datasource.DataSourceClient;
import org.md2k.datakitapi.source.datasource.DataSourceType;
import org.md2k.datakitapi.time.DateTime;
import org.md2k.mcerebrum.core.data_format.DATA_QUALITY;
import org.md2k.mcerebrum.core.data_format.DataFormat;
import org.md2k.mcerebrum.core.data_format.ResultType;
import org.md2k.scheduler.datakit.DataKitManager;

import java.util.ArrayList;
import java.util.List;

public class is_driving extends Function {
    public is_driving(DataKitManager dataKitManager) {
        super(dataKitManager);
    }

    public Expression add(Expression e) {
        e.addLazyFunction(e.new LazyFunction("is_driving", -1) {
            @Override
            public Expression.LazyNumber lazyEval(List<Expression.LazyNumber> lazyParams) {
                DataSourceBuilder d = createDataSource(lazyParams, 2);
                long sTime = lazyParams.get(0).eval().longValue();
                long eTime = lazyParams.get(1).eval().longValue();
                boolean isDriving = isDriving(sTime, eTime);
                if(isDriving) return create(1);
                else return create(0);
            }
        });
        return e;
    }

    public boolean isDriving(long sTime, long eTime) {
        ApplicationBuilder applicationBuilder = new ApplicationBuilder().setId("org.md2k.phonesensor");
        DataSourceBuilder dataSourceBuilder = new DataSourceBuilder().setType(DataSourceType.ACTIVITY_TYPE).setApplication(applicationBuilder.build());
        ArrayList<DataSourceClient> dataSourceClientArrayList = dataKitManager.find(dataSourceBuilder.build());
        if (dataSourceClientArrayList.size() == 0) return false;
        ArrayList<DataType> dataTypes = dataKitManager.query(dataSourceClientArrayList.get(0), sTime, eTime);
        if (dataTypes.size() == 0) return false;

        for (int i = 0; i < dataTypes.size(); i++) {
            double samples[] = ((DataTypeDoubleArray) dataTypes.get(i)).getSample();
            if (samples[DataFormat.ActivityType.Type] == ResultType.ActivityType.IN_VEHICLE)
                return true;
        }
        return false;
    }

    public String prepare(String s) {
        return s;
    }
}
