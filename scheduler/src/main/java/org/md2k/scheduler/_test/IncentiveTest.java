package org.md2k.scheduler._test;
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

import android.content.Context;
import android.util.Log;

import org.md2k.scheduler.State;
import org.md2k.scheduler.operation.incentive.IncentiveOperation;

import rx.Observer;

public class IncentiveTest {
/*
    public static void testIncentiveOp(Context context){
        String[] message = new String[]{
                "Thank you. You will be paid $<AMOUNT> for taking the survey and wearing the sensors for more than 60% of the time since your last survey.",
                "You will be paid $<AMOUNT>",
                "Total Earning: $<TOTAL_AMOUNT>"
        };

        IncentiveOperation i = new IncentiveOperation(1.25, message, 3000);
        i.getObservable(context,0).subscribe(new Observer<State>() {
            @Override
            public void onCompleted() {
                Log.d("abc.IncentiveTest","onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("abc.IncentiveTest","onError()..e="+e.toString());

            }

            @Override
            public void onNext(State state) {
                Log.d("abc.IncentiveTest","onNext().."+state.getMessage());
            }
        });
    }
*/
}