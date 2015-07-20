package org.streamkit.transcoding.job;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.streamkit.transcoding.model.TranscodingModel;
import org.streamkit.transcoding.model.VideoDestination;

import java.util.logging.Logger;

/**
 * Created by ddascal on 22/01/15.
 */
public class PersistVideoS3 implements Step {
    private Logger logger = Logger.getLogger(ReadConfigJson.class.getName());

    @Override
    public String getName() {
        return "persist-video-s3";
    }

    @Override
    public boolean isAllowStartIfComplete() {
        return true; // allow restart in case of failure
    }

    @Override
    public int getStartLimit() {
        return 5; // retry maximum 5 types to upload the file
    }

    @Override
    public void execute(StepExecution stepExecution) throws JobInterruptedException {
        logger.info("Persisting video to S3");
        TranscodingModel model = (TranscodingModel) stepExecution.getJobExecution().getExecutionContext().get("model");
        if (! model.getDestination().getType().equals(VideoDestination.AWSS3)) {
            stepExecution.setStatus(BatchStatus.COMPLETED);
            return;
        }
        stepExecution.addFailureException(new Exception("Upload to S3 not implemented yet."));
    }
}
