package ch.kusar.contraceptivetimer.businessobjects;

import java.io.Serializable;

public class LicenseStatus implements Serializable, FileStorageWriter {
	private static final long serialVersionUID = -323916888849271339L;
	private final String fileName = "ContraceptiveLicense";
	private boolean paid = false;

	public LicenseStatus(boolean paid) {
		this.paid = paid;
	}

	public LicenseStatus() {
		this.paid = false;
	}

	public boolean isPaid() {
		return this.paid;
	}

	public void setPaid(boolean paid) {
		if (!this.paid) {
			this.paid = paid;
		}
	}

	public String getFileName() {
		return this.fileName;
	}
}