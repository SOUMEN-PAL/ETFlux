package org.soumen.home.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.point
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import org.soumen.home.domain.dataModels.MonthlyTimeSeriesData
import org.soumen.shared.domain.Resources
import java.util.Arrays.fill



import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer.Line
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.shape.Shape

@Composable
fun CartesianLineChart(
    modifier: Modifier = Modifier,
    monthlyData :  List<MonthlyTimeSeriesData>,
    modelProducer: CartesianChartModelProducer,
) {

    CartesianChartHost(
        chart =
            rememberCartesianChart(
                rememberLineCartesianLayer(
                    lineProvider = LineCartesianLayer.LineProvider.series(
                        // Style for the first line in your data
                        LineCartesianLayer.rememberLine(
                            fill = LineCartesianLayer.LineFill.single(fill(Resources.Colors.ascentGreen)),
                            pointProvider = LineCartesianLayer.PointProvider.single( // 👈 Wrap it here
                                point = LineCartesianLayer.point(
                                    component = rememberShapeComponent(shape = Shape.Rectangle , fill = Fill(color = Resources.Colors.ascentGreen.toArgb())),
                                    size = 6.dp
                                )
                            )

                        ),
                        // Style for the second line (if it exists)
                        LineCartesianLayer.rememberLine(
                            fill = LineCartesianLayer.LineFill.single(fill(Resources.Colors.ascentRed)),
                            pointProvider = LineCartesianLayer.PointProvider.single( // 👈 Wrap it here
                                point = LineCartesianLayer.point(
                                    component = rememberShapeComponent(shape = Shape.Rectangle , fill = Fill(color = Resources.Colors.ascentRed.toArgb())),
                                    size = 6.dp
                                )
                            )
                        )
                        // Add more rememberLine calls for more lines...
                    )
                ),
                startAxis = VerticalAxis.rememberStart(
                    title = "Price",
                    titleComponent = rememberTextComponent(
                        color = Resources.Colors.textColor    // ✅ axis title color
                    ),
                    label = rememberTextComponent(
                        color = Resources.Colors.textColor   // ✅ Y-axis tick labels color
                    ),
                    guideline = null
                ),
                bottomAxis = HorizontalAxis.rememberBottom(
                    title = "Month",
                    titleComponent = rememberTextComponent(
                        color = Resources.Colors.textColor   // ✅ axis title color
                    ),
                    label = rememberTextComponent(
                        color = Color.Black   // ✅ X-axis tick labels color
                    ),
                    guideline = null
                ),
            ),
        modelProducer = modelProducer,
        modifier = modifier,
    )

}
