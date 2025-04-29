package dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class EspaciosLibresRespuesta {

    @Override
	public String toString() {
		return "EspaciosLibresRespuesta [embedded=" + embedded + "]";
	}

	@SerializedName("_embedded")
    private Embedded embedded;

    public List<EspacioLibreDto> getEspacios() {
        return embedded != null ? embedded.espacioLibreDtoList : null;
    }

    public static class Embedded {
        @SerializedName("espacioLibreDtoList")
        List<EspacioLibreDto> espacioLibreDtoList;
    }
}
