package ru.ural.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import ru.ural.dto.InspectionDto;
import ru.ural.dto.accidents.AccidentDto;
import ru.ural.dto.accidents.AccidentInfoDto;
import ru.ural.dto.wanted.WantedDto;
import ru.ural.dto.wanted.WantedInfoDto;
import ru.ural.properties.AccidentProperty;
import ru.ural.properties.InspectionProperty;
import ru.ural.properties.WantedProperty;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GaiService {

    private static final String WANTED_DESCRIPTION_PATTERN = "Пропала машина с вин-номером: %s";

    private static final String ACCIDENT_DESCRIPTION_PATTERN = "Машина с вин-номером: %s была участником аварии";

    private static final String INSPECTION_DESCRIPTION_PATTERN = "Тех. осмотр для машины: %s - проведен";

    private final WantedProperty wantedProperty;

    private final AccidentProperty accidentProperty;

    private final InspectionProperty inspectionProperty;

    public WantedInfoDto getWantedInfo(@NonNull String vin) {
        boolean isGoodMock = isGoodMock(vin, wantedProperty.getGoodVins());
        if (isGoodMock) {
            return WantedInfoDto.builder()
                    .isWanted(false)
                    .build();
        }

        WantedDto wantedDto = WantedDto.builder()
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 2, 2))
                .description(WANTED_DESCRIPTION_PATTERN.formatted(vin))
                .build();

        return WantedInfoDto.builder()
                .isWanted(true)
                .wantedList(List.of(wantedDto))
                .build();
    }

    public AccidentInfoDto getAccidentInfo(@NonNull String vin) {
        boolean isGoodMock = isGoodMock(vin, accidentProperty.getGoodVins());
        if (isGoodMock) {
            return AccidentInfoDto.builder()
                    .isAccident(false)
                    .build();
        }

        AccidentDto accidentDto = AccidentDto.builder()
                .date(LocalDate.of(2025, 1, 1))
                .description(ACCIDENT_DESCRIPTION_PATTERN.formatted(vin))
                .build();

        return AccidentInfoDto.builder()
                .isAccident(true)
                .accidents(List.of(accidentDto))
                .build();
    }

    public InspectionDto getInspectionInfo(@NonNull String vin) {
        boolean isGoodMock = isGoodMock(vin, inspectionProperty.getGoodVins());
        if (isGoodMock) {
            return InspectionDto.builder()
                    .date(LocalDate.now())
                    .description(INSPECTION_DESCRIPTION_PATTERN.formatted(vin))
                    .build();
        }

        return null;
    }

    private boolean isGoodMock(String vin, List<String> goodVins) {
        return goodVins.contains(vin);
    }

}
