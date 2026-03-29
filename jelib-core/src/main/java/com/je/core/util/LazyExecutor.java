package com.je.core.util;

import java.util.function.Consumer;

/**
 * LazyExecutor is an executor that receives request to execute
 * code but executes only if the determined time has passed.
 */
public class LazyExecutor {
    /**
     * Time that executor rests for in millis.
     */
    private final long mRestTimeMillis;

    /**
     * Alternative executor, if method {@link #execute(Bundle)} is not overridden.
     * Then, this {@link Consumer<Bundle>} will do the job.
     */
    private final Consumer<Bundle> mExecutor;

    /**
     * Last time executor executed something in milliseconds.
     */
    private long mLastExecutionTimeMillis = 0;

    /**
     * Constructor initializes executor from rest time.
     * Method {@link #execute(Bundle)} may be overridden when using this constructor.
     * @param restTimeMillis time that executor rests for.
     */
    public LazyExecutor(long restTimeMillis) {
        mRestTimeMillis = restTimeMillis;
        mExecutor = null;
    }

    /**
     * Constructor initializes executor from rest time and executor.
     * @param executor       executor to use.
     * @param restTimeMillis time that executor rests for.
     */
    public LazyExecutor(Consumer<Bundle> executor, long restTimeMillis) {
        mRestTimeMillis = restTimeMillis;
        mExecutor = executor;
    }

    /**
     * Requests an execution from lazy executor.
     * If executor is ready, this action will execute.
     * @param bundle bundle to use if execution happens.
     */
    public final void request(Bundle bundle) {
        long nextExecuteTime = mRestTimeMillis + mLastExecutionTimeMillis;
        if(nextExecuteTime < System.currentTimeMillis()) {
            mLastExecutionTimeMillis = System.currentTimeMillis();
            execute(bundle);
        }
    }

    /**
     * Executes code when lazy executor wants to.
     * @param bundle params to give to executor.
     */
    protected void execute(Bundle bundle) {
        if(mExecutor!=null)
            mExecutor.accept(bundle);
    }

    /**
     * Method to get rest time of executor.
     * @return returns how much executor rests for as long.
     */
    public final long getRestTime() {
        return mRestTimeMillis;
    }
}
